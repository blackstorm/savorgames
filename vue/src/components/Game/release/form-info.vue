<template>
  <form class="form-game">
    <div class="tips">
      <ol>
        <li>游戏的中文名</li>
        <li>外文名称(可留空)</li>
        <li>游戏简介</li>
        <li>发售日期或上线日期</li>
      </ol>
    </div>
    <validator name="gameInfoValidation">
      <div class="form-group">
        <input class="form-input" type="text" placeholder="中文名称" v-model="form.chineseName" v-validate:name="{ required: true, minlength: 1, maxlength: 30 }"/>
        <span class="error" v-show:="$gameInfoValidation.name.invalid">如果就一个名称填到这里就好啦！最多30个字符哟</span>
      </div>
      <div class="form-group">
        <input class="form-input" type="text" placeholder="外文名称" v-model="form.otherName" />
      </div>
      <div class="form-group">
        <input class="form-input" type="date" v-model="form.date" v-validate:date="{required: true}" />
        <span class="error" v-show:="$gameInfoValidation.date.invalid">发售日期还是必须滴</span>
      </div>
      <div class="form-group">
        <textarea class="form-input" v-model="form.description" placeholder="简单介绍一下这个游戏的特点" rows="3" v-validate:description="{ required: true, minlength: 16, maxlength: 140 }"></textarea>
        <span class="error" v-show:="$gameInfoValidation.description.invalid">简介不能太少（16个字符）或太多（140个字符）</span>
      </div>
      <div class="form-group">
        <button class="btn btn-primary btn-block" :disabled="$gameInfoValidation.invalid" @click.prevent="infoButtonOnClick($gameInfoValidation)">下一步</button>
      </div>
    </validator>
  </form>
</template>
<script>
export default {
  props: ['form', 'show'],
  methods: {
    infoButtonOnClick() {
      if (this.form.chineseName === null && this.form.otherName === null) {
        return;
      }
      if (this.form.chineseName === '' && this.form.otherName === '') {
        return;
      }
      this.show = 'type';
    }
  }
};
</script>
<style>
.form-game {
  width: 100%;
  padding-top: 20px;
  padding-bottom: 20px;
}
.form-game .tips {
  padding: 10px;
  background-color: #FFFFCC;
  border-radius: 4px;
  border:1px solid #eee;
  margin-bottom: 16px;
  font-size: 14px;
  color:#666666;
}
.form-game .error{
  padding: 5px;
  font-size: 13px;
  color:#CC0000;
}
</style>