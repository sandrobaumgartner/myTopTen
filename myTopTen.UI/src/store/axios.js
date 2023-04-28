import axios from 'axios'
import router from '@/router'

const instance = axios.create({
  baseURL: 'http://localhost:1337/api',
})

instance.defaults.withCredentials = true

instance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response.status === 401) {
      if (error.response.data.path !== '/api/auth/signin') {
        router.push('/login')
      }
    }
    return Promise.reject(error)
  },
)

export default instance
