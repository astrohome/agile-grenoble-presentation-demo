<template>
  <div>
      <div v-bind:key="category.id" v-for="category in categories">
          <li>
              <router-link :to="{ name: 'one-category', params: { categoryId: category.id }}">{{category.name}}</router-link>
          </li>
      </div>
  </div>
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