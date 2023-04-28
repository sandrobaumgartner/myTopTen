import axios from './axios.js'

export default {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {
    async getMoviesContainingTitle(state, partOfTitle) {
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
  },
}
