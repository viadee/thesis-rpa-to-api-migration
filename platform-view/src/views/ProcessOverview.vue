<template>
  <div class="process-overview">
      <h2>Overview of Process Definitions</h2>
      <hr />
      <process-definition-list-comp />
  </div>
</template>

<script>
import ProcessDefinitionListComp from "../components/Process Overview/ProcessDefinitionListComp.vue"

export default {
    name: "ProcessOverview",
    components: {
        ProcessDefinitionListComp
    }
}
</script>

<style>
.process-overview {
    margin-top: 20px;
    margin-left: -236px;;
    margin-right: 20px;
}
.process-overview h2 {
    margin-left: 30px;
    margin-bottom: 5px;
}
.process-overview hr {
  color: lightgrey;
}
</style>