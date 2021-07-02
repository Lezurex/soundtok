<template>
  <div id="clipList">
    <Clip :clipId="1"></Clip>
  </div>
</template>

<script>
import Clip from "@/components/Clip";
export default {
  name: "Home",
  components: {Clip}
}
</script>

<style scoped>
#clipList {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>