<template>
  <div class="rpaBots">
      <h2>Prioritization of RPA Bots</h2>
      <hr />
      <PrioritizationListComp />
  </div>
</template>

<script>
import PrioritizationListComp from '../components/RPA/RPAPrioritizationListComp.vue'
export default {
  name: "RPA",
  components: {
    PrioritizationListComp
  }
}
</script>

<style>
.rpaBots {
    margin-top: 20px;
    margin-left: -236px;;
    margin-right: 20px;
}
.rpaBots h2 {
    margin-left: 30px;
    margin-bottom: 5px;
}
.rpaBots hr {
  color: lightgrey;
}
</style>