<template>
  <div>
    <Clip :clipId="Number.parseInt($route.params.clipId)"></Clip>
  </div>
</template>

<script>
import Clip from "@/components/Clip";

export default {
  name: "SingleClip",
  components: {Clip}
}
</script>

<style scoped>
div {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>