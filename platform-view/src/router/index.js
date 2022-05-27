import Vue from 'vue'
import VueRouter from 'vue-router'
import ProcessOverview from '../views/ProcessOverview.vue'
import RPABots from '../views/RPABots.vue'
import Migration from '../views/Migration.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/overview',
    name: 'Process Overview',
    component: ProcessOverview
  },
  {
    path: '/rpa',
    name: 'RPA Prioritization ',
    component: RPABots
  },
  {
    path: '/migration',
    name: 'Migration',
    component: Migration
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
