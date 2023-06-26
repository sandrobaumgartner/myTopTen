<template>
  <div>
    <ul v-if="showList" class="list">
      <li v-for="item in movieList" v-bind:key="item">
        <div class="index">{{ item.position }}.</div>
        <div class="item">{{ item.title }} ({{ item.releaseYear }})</div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'UserPage',
  data() {
    return {
      userId: this.$route.params.userId,
      movieList: [],
      showList: false,
    }
  },
  mounted() {
    setTimeout(() => {
      this.getMoviesForUser()
    }, 200)
  },
  methods: {
    getMoviesForUser() {
      this.showList = false
      this.$store
        .dispatch('movies/getMovieListByUserId', this.userId)
        .then((response) => {
          this.movieList = response
          this.showList = true
        })
    },
  },
}
</script>

<style scoped>
.list {
  margin-top: 55px;
  list-style-type: none;
  text-align: center;
  padding: 0;
}

.index {
  float: left;
  font-size: xx-large;
  text-align: center;
  padding-top: 3px;
  padding-left: 10px;
  position: absolute;
}

.item {
  padding: 10px;
  margin: 5px;
  font-size: x-large;
  text-align: center;
  background-color: lightgrey;
  border-radius: 10px;
}
</style>
