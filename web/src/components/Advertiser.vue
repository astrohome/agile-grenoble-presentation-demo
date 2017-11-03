<template>
  <div>
      <router-link :to="{ name: 'list-categories' }">All categories</router-link>
      <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'Advertiser'
}
</script>
