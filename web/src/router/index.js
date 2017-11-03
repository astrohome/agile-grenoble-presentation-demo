import Vue from 'vue'
import Router from 'vue-router'
import ListCategories from '@/components/advertiser/ListCategories'
import Category from '@/components/advertiser/Category'
import Product from '@/components/advertiser/Product'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/advertiser',
      name: 'index-advertiser',
      component: ListCategories
    },
    {
      path: '/advertiser/category/:categoryId',
      name: 'one-category',
      component: Category,
      props: (route) => ({
        categoryId: parseInt(route.params.categoryId)
      })
    },
    {
      path: '/advertiser/category/:categoryId/product/:productId',
      name: 'one-product',
      component: Product,
      props: (route) => ({productId: parseInt(route.params.productId),
        categoryId: parseInt(route.params.categoryId)})
    }
  ]
})
