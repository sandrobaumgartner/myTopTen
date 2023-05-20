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
    async logout() {
      return new Promise((resolve, reject) => {
        axios
          .get('/auth/signout')
          .then(() => {
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    async checkLogin() {
      return new Promise((resolve, reject) => {
        axios
          .get('/test/user')
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
