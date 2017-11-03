<template>
  <div>
    <nav class="breadcrumb" aria-label="breadcrumbs">
        <ul>
            <li><router-link :to="{ name: 'index-advertiser'}">FRAC</router-link></li>
            <li class="is-active"><router-link  :to="{ name: 'index-advertiser'}">Categories</router-link></li>
        </ul>
    </nav>
      <div v-bind:key="category.id" v-for="category in $ds.categories">
          <li>
              <router-link :to="{ name: 'one-category', params: { categoryId: category.id }}">{{category.name}}</router-link>
          </li>
      </div>
  </div>
</template>