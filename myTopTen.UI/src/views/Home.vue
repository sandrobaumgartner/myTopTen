<template>
  <div>
    <div class="searchbar">
      <input
        type="text"
        placeholder="Search movie"
        v-bind:class="{
          roundEdges: searchBarRoundEdges,
          noRoundEdges: !searchBarRoundEdges,
        }"
        v-model="searchTerm"
        @input="search"
        @focus="search"
        @blur="empty"
      />
      <div class="suggestions" v-if="suggestions.length">
        <ul>
          <li v-for="suggestion in suggestions" :key="suggestion.id">
            {{ suggestion.title }} ({{ suggestion.releaseYear }})
            <span
              v-if="suggestion.genre == 'Action'"
              class="badge rounded-pill text-bg-danger"
              >{{ suggestion.genre }}</span
            >
            <span
              v-else-if="suggestion.genre == 'Adventure'"
              class="badge rounded-pill text-bg-success"
              >{{ suggestion.genre }}</span
            >
            <span
              v-else-if="suggestion.genre == 'Comedy'"
              class="badge rounded-pill text-bg-warning"
              >{{ suggestion.genre }}</span
            >
            <span
              v-else-if="suggestion.genre == 'Animation'"
              class="badge rounded-pill animation"
              >{{ suggestion.genre }}</span
            >
            <span
              v-else-if="suggestion.genre == 'Crime'"
              class="badge rounded-pill crime"
              >{{ suggestion.genre }}</span
            >
            <span
              v-else-if="suggestion.genre == 'Documentary'"
              class="badge rounded-pill documentary"
              >{{ suggestion.genre }}</span
            >
            <span
              v-else-if="suggestion.genre == 'Drama'"
              class="badge rounded-pill drama"
              >{{ suggestion.genre }}</span
            >
            <span
              v-else-if="suggestion.genre == 'Horror'"
              class="badge rounded-pill horror"
              >{{ suggestion.genre }}</span
            >
            <span v-else class="badge rounded-pill text-bg-primary">{{
              suggestion.genre
            }}</span>
            <CButton
              type="button"
              class="btn btn-dark plus-button"
              v-on:click="addMovieToList(suggestion)"
            >
              <CIcon :icon="icon.cilPlus" />
            </CButton>
          </li>
        </ul>
      </div>
    </div>
    <draggable
      v-if="showList"
      v-model="movieList"
      :transition="200"
      class="draggable"
    >
      <template v-slot:item="{ item }">
        <div class="index">{{ item.position }}.</div>
        <div class="list-item">
          {{ item.title }} ({{ item.releaseYear }})
          <CButton
            type="button"
            class="btn btn-dark text-white delete-button"
            v-on:click="deleteMovie(item)"
          >
            <CIcon :icon="icon.cilTrash" />
          </CButton>
        </div>
      </template>
    </draggable>
  </div>
</template>

<script>
import draggable from 'vue3-draggable'
import { CIcon } from '@coreui/icons-vue'
import * as icon from '@coreui/icons'

export default {
  name: 'Home',
  components: {
    draggable,
    CIcon,
  },
  setup() {
    return {
      icon,
    }
  },
  data() {
    return {
      searchTerm: '',
      suggestions: [],
      movieList: [],
      showList: false,
    }
  },
  computed: {
    searchBarRoundEdges() {
      return this.suggestions.length <= 0
    },
  },
  beforeMount() {
    this.$store.dispatch('userManagement/checkLogin')
  },
  mounted() {
    this.getMoviesForUser()
  },
  methods: {
    getMoviesForUser() {
      this.showList = false
      this.$store.dispatch('userManagement/getUser').then(() => {
        this.$store
          .dispatch(
            'movies/getMovieListByUserId',
            this.$store.getters['userManagement/user'].id,
          )
          .then((response) => {
            this.movieList = response
            this.showList = true
          })
      })
    },
    search() {
      this.$store
        .dispatch('movies/getMoviesContainingTitle', this.searchTerm)
        .then((response) => {
          this.suggestions = response
        })
    },
    empty() {
      setTimeout(() => {
        this.suggestions = []
      }, 200)
    },
    addMovieToList(movie) {
      movie.position = this.movieList.length + 1
      let moviePositionModel = {
        movieId: movie.id,
        position: movie.position,
      }

      if (this.movieList.length < 10) {
        this.$store
          .dispatch('movies/addMovieToList', moviePositionModel)
          .then(() => {
            this.showList = false
            let moviePosition = {
              movieId: movie.id,
              title: movie.title,
              position: movie.position,
              releaseYear: movie.releaseYear,
            }

            this.movieList.push(moviePosition)
            this.$nextTick(() => {
              this.showList = true
            })
          })
      }
    },
    deleteMovie(movie) {
      let deleteMovie = {
        movieId: movie.movieId,
      }
      this.$store
        .dispatch('movies/deleteMovieFromList', deleteMovie)
        .then(() => {
          this.getMoviesForUser()
        })
    },
  },
  watch: {
    movieList() {
      let count = 1
      this.movieList.forEach((movie) => {
        movie.position = count
        count++
      })
      this.$store.dispatch('movies/updatePositions', this.movieList)
    },
  },
}
</script>

<style scoped>
.searchbar {
  position: relative;
  display: flex;
  align-items: center;
}

.searchbar input[type='text'] {
  width: 100%;
  height: 40px;
  padding: 10px;
  border: none;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  font-size: 16px;
}

.roundEdges {
  border-radius: 20px;
}

.noRoundEdges {
  border-radius: 10px 10px 0 0;
}

.searchbar input[type='text']:focus {
  outline: none;
}

.suggestions {
  position: absolute;
  top: 40px;
  left: 0;
  z-index: 1;
  width: 100%;
  background-color: #fff;
  border-radius: 0 0 10px 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.suggestions ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.suggestions li {
  padding: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.suggestions li:hover {
  background-color: #f5f5f5;
}

.badge {
  color: white !important;
}

.animation {
  background-color: #ff69b4;
}

.crime {
  background-color: black;
}

.documentary {
  background-color: green;
}

.drama {
  background-color: cadetblue;
}

.horror {
  background-color: crimson;
}

.draggable {
  margin-top: 55px;
}

.index {
  float: left;
  cursor: pointer;
  font-size: xx-large;
  text-align: center;
  padding-top: 3px;
  padding-left: 10px;
  position: absolute;
}

.list-item {
  cursor: pointer;
  padding: 10px;
  margin: 5px;
  font-size: x-large;
  text-align: center;
  background-color: lightgrey;
  border-radius: 10px;
}

.plus-button {
  float: right;
  padding: 0 5px 0 5px;
}

.delete-button {
  float: right;
  padding: 0 5px 0 5px;
  margin-top: 5px;
}
</style>
