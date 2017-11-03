import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ListCategories from '@/components/advertiser/ListCategories'
import Category from '@/components/advertiser/Category'
import Product from '@/components/advertiser/Product'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: HelloWorld
    },
    {
      path: '/category/:id',
      component: ListCategories,
      children: [
        {
          path: '',
          name: 'one-category',
          component: Category,
          props: true
        }
      ]
    },
    {
      path: '/product/:id',
      name: 'one-product',
      component: Product,
      props: (route) => ({id: parseInt(route.params.id)})
    }
  ]
})
