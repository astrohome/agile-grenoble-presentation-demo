<template>
  <section class="section">
      <router-link :to="{name: 'one-category', params: {categoryId: categoryId}}">Retour</router-link>
      <br>
      <div class="container">
          <h1 class="title">{{product.name}}</h1>
          <h2 class="subtitle">
              {{product.subcategory}}
          </h2>
          <div class="columns">
            <div class="column">
                <img :src="getPic(productId)">
                <br>
                <span class="price">{{product.price}}</span>
            </div>
            <div class="column">
                {{product.description}}
            </div>
          </div>          
      </div>
  </section>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    productId: {
      type: Number,
      required: true
    },
    categoryId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      product: {}
    }
  },
  methods: {
    getPic (productId) {
      return require('@/assets/advertiser/' + this.productId + '.jpg')
    }
  },
  mounted () {
    this.product = this.$ds.categories.find(x => x.id === this.categoryId).products.find(x => x.id === this.productId)
    axios.get('/api/advertiser-tracker/view?productid=' + this.productId + '&userid=' + 5)
  }
}
</script>
