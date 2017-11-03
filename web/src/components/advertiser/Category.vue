<template>
  <div v-cloak>
      <router-link :to="{name: 'index-advertiser'}">
             Retour
            </router-link>
    <div v-bind:key="product.id" v-for="product in categoryProducts">
      <section class="section">
      <article class="media">
        <figure class="media-left">
          <p class="image is-400x400">
            <img :src="getPic(product.id)">
          </p>
        </figure>
        <div class="media-content">
          <div class="content">
            <router-link :to="{ name: 'one-product', params: { 'productId': product.id, 'categoryId': categoryId }}">
              {{ product.name }}
            </router-link>
          </div>
        </div>
      </article>
      </section>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    categoryId: {
      type: Number,
      required: true
    }
  },
  methods: {
    getPic (productId) {
      return require('@/assets/advertiser/' + productId + '.jpg')
    }
  },
  data () {
    return {
      categoryProducts: [],
      allProducts: new Map()
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
  },
  mounted () {
    this.categoryProducts = this.allProducts.get(this.categoryId)
  }
}
</script>

