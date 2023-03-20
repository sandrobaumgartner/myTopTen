import axios from './axios.js'

export default {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {
    async register(state, userData) {
      return new Promise((resolve, reject) => {
        axios
          .post('/auth/signup', userData)
          .then(() => {
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    async login(state, userCredentials) {
      return new Promise((resolve, reject) => {
        axios
          .post('/auth/signin', userCredentials)
          .then(() => {
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    async test() {
      return new Promise((resolve, reject) => {
        axios
          .get('/test/user', {
            withCredentials: true,
          })
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
