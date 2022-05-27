<template>
  <CPLIDProviderView />
</template>

<script>
import CPLIDProviderView from './components/CPLIDProviderView.vue'

export default {
  name: 'App',
  components: {
    CPLIDProviderView
  },
  beforeCreate() {
    document.querySelector('body').setAttribute('style', 'background-color: black');
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: white;
  margin-top: 60px;
}
</style>
