<template>
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
          <span v-else class="badge rounded-pill text-bg-primary">{{
            suggestion.genre
          }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      searchTerm: '',
      suggestions: [],
      count: 0,
    }
  },
  computed: {
    searchBarRoundEdges() {
      return this.suggestions.length <= 0
    },
  },
  methods: {
    //@blur="empty"
    search() {
      this.$store
        .dispatch('movies/getMoviesContainingTitle', this.searchTerm)
        .then((response) => {
          this.suggestions = response
        })
    },
    empty() {
      this.suggestions = []
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
</style>
