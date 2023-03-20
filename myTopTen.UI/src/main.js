import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import CoreuiVue from '@coreui/vue'
import CIcon from '@coreui/icons-vue'
import { iconsSet as icons } from '@/assets/icons'
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'

library.add(faUserSecret)

const app = createApp(App)
app.use(store)
app.use(router)
app.use(CoreuiVue)
app.component('font-awesome-icon', FontAwesomeIcon)
app.provide('icons', icons)
app.component('CIcon', CIcon)

app.mount('#app')
