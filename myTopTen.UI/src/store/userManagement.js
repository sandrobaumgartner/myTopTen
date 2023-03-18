import axios from './axios.js'

export default {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {
    async register(state, userData) {
      await axios
        .post('/auth/signup', userData)
        .then((response) => {
          return Promise.resolve(response.data)
        })
        .catch((error) => {
          return Promise.reject(error)
        })
    },
  },
}
