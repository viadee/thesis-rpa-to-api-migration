<template>
  <div class="migration">
      <h2>Migration</h2>
      <hr />
      <RPAMigrationListComp /> 
  </div>
</template>

<script>
import RPAMigrationListComp from "../components/Migration/RPAMigrationListComp.vue"

export default {
    name: "Migration",
    components: {
      RPAMigrationListComp
    }
}
</script>

<style>
.migration {
    margin-top: 20px;
    margin-left: -236px;;
    margin-right: 20px;
}
.migration h2 {
    margin-left: 30px;
    margin-bottom: 5px;
}
.migration hr {
  color: lightgrey;
}
</style>