<template>
  <div v-cloak>
    <div v-bind:key="product.id" v-for="product in products.get(id)">
      <section class="section">
      <article class="media">
        <figure class="media-left">
          <p class="image is-400x400">
            <img :src="getPic(product.id)">
          </p>
        </figure>
        <div class="media-content">
          <div class="content">
            <router-link :to="{ name: 'one-product', params: { id: product.id }}">
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

