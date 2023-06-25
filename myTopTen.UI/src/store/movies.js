import axios from './axios.js'

export default {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {
    async getMoviesContainingTitle(store, partOfTitle) {
      return new Promise((resolve, reject) => {
        axios
          .get('/movie/byPartOfTitle', { params: { partOfTitle: partOfTitle } })
          .then((response) => {
            resolve(response.data)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    async getMovieListByUserId(store, userId) {
      return new Promise((resolve, reject) => {
        axios
          .get('/movie/list', { params: { userId: userId } })
          .then((response) => {
            resolve(response.data)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    async addMovieToList(store, moviePosition) {
      return new Promise((resolve, reject) => {
        axios
          .post('/movie/addToList', moviePosition)
          .then(() => {
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    async deleteMovieFromList(store, movie) {
      return new Promise((resolve, reject) => {
        axios
          .post('/movie/delete', movie)
          .then(() => {
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    async updatePositions(store, movies) {
      return new Promise((resolve, reject) => {
        axios
          .post('/movie/updatePositions', movies)
          .then(() => {
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
  },
}
