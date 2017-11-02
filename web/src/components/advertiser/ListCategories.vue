<template>
  <section>
      <div v-bind:key="category.id" v-for="category in categories">
          <li>
              <router-link :to="{ name: 'one-category', params: { id: category.id }}">{{category.name}}</router-link>
          </li>
      </div>
      <router-view></router-view>
  </section>
</template>

<script>
export default {
  data () {
    return {
      categories: [
        {
          name: 'ORDINATEURS PORTABLES',
          id: 1
        },
        {
          name: 'IMPRIMANTES ET SCANNERS',
          id: 2
        }
      ]
    }
  }
}
</script>