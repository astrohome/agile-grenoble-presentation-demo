import Vue from 'vue'
import App from './App'
import router from './router'
import Buefy from 'buefy'

Vue.use(Buefy)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
