import axios from './axios.js'

export default {
  namespaced: true,
  state: {
    user: null,
  },
  getters: {
    user(state) {
      return state.user
    },
  },
  mutations: {},
  actions: {
    async register(store, userData) {
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
    async login(store, userCredentials) {
      return new Promise((resolve, reject) => {
        axios
          .post('/auth/signin', userCredentials)
          .then(() => {
            localStorage.setItem('username', userCredentials.username)
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
    async getUser(store) {
      return new Promise((resolve, reject) => {
        axios
          .get('/user', {
            params: { username: localStorage.getItem('username') },
          })
          .then((response) => {
            store.state.user = response.data
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    async getUserByPartOfUsername(store, partOfUsername) {
      return new Promise((resolve, reject) => {
        axios
          .get('/user/byPartOfUsername', {
            params: {
              partOfUsername,
            },
          })
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
