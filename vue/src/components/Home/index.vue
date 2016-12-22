<template>
  <div id="home">
    <Homebanner></Homebanner>
    <Menutopic></Menutopic>
  </div>
</template>
<script>
import Homebanner from './homebanner.vue';
import Menutopic from './menutopic.vue';

export default {
  components: {
    Homebanner,
    Menutopic
  }
};
</script>
<style>
</style>