<template>
  <div v-cloak>
          <nav class="breadcrumb" aria-label="breadcrumbs">
        <ul>
            <li><router-link :to="{ name: 'index-advertiser'}">FRAC</router-link></li>
            <li><router-link  :to="{ name: 'index-advertiser'}">Categories</router-link></li>
            <li class="is-active"><router-link  :to="{ name: 'index-advertiser'}">{{currentCategory.name}}</router-link></li>
        </ul>
    </nav>
      <router-link :to="{name: 'index-advertiser'}">
             Retour
            </router-link>
    <div v-bind:key="product.id" v-for="product in currentCategory.products">
      <section class="section product">
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
      currentCategory: {}
    }
  },
  mounted () {
    this.currentCategory = this.$ds.categories.find(x => x.id === this.categoryId)
  }
}
</script>

<style lang="scss" scoped>
section.product {
    /* Add shadows to create the "card" effect */
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    margin-top: 1em;
}
/* On mouse-over, add a deeper shadow */
section.product:hover {
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
}
</style>
