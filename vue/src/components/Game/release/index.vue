<template>
  <div id="game-release" class="container">
    <Info :form.sync="form" :show.sync="show" v-show:="show == 'info' ? true : false"></Info>
    <Type :form.sync="form" :show.sync="show" v-show:="show == 'type' ? true : false"></Type>
  </div>
</template>
<script>
import Info from '../release/form-info.vue';
import Type from '../release/form-type.vue';

export default {
  components: {
    Info,
    Type
  },
  data() {
    return {
      show: 'info',
      form: {
        chineseName: null,
        otherName: null,
        description: null,
        date: null,
        style: 0,
        platforms: []
      }
    };
  }
};
</script>
<style>
#game-release {
  width: 960px;
  border:1px solid #ee;
  background-color: #f2f2f2;
}
</style>