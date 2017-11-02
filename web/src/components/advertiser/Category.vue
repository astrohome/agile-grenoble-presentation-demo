<template>
  <section v-cloak>
      Products:
        <div v-bind:key="product.id" v-for="product in products.get(id)">            
            <div class="product-img">
                <img :src="getPic(product.id)">
            </div>            
            <router-link :to="{ name: 'one-product', params: { id: product.id }}">
                {{ product.name }}
            </router-link>
        </div>
  </section>
</template>

<script>
export default {
  props: ['id'],
  methods: {
    getPic (productId) {
      return require('@/assets/advertiser/' + productId + '.jpg')
    }
  },
  data () {
    return {
      products: new Map()
      .set(1, [
        {
          id: 101,
          name: 'Dell XPS13'
        },
        {
          id: 102,
          name: 'Dell XPS15'
        }
      ])
      .set(2, [
        {
          id: 201,
          name: 'Imprimante HP Envy 4527 Multifonctions WiFi Noir'
        },
        {
          id: 202,
          name: 'Imprimante HP Envy 5640 All-In-One'
        }
      ])
    }
  }
}
</script>

<style scoped>
.product-img {
    width: 400px;
    height: 400px;
}

.product-img img {
max-width:100%;
max-height:100%;
height: auto;
width:auto;
}
</style>
