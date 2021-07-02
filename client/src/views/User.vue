<template>
  <div id="root" v-if="notFound === false">
    <h1>@{{user.username}}</h1>
    <div id="clipList">
      <Clip v-for="clip of clips" :key="clip.id" :clip-id="clip.id"></Clip>
    </div>
  </div>
  <div v-else-if="notFound === undefined"><h1 style="color: white">Loading...</h1></div>
  <div v-else><h1 style="color: white">User not found</h1></div>
</template>

<script>
import Clip from "@/components/Clip";
export default {
  name: "User",
  components: {Clip},
  data() {
    return {
      user: {},
      clips: {},
      notFound: undefined
    }
  },
  mounted() {
    this.load();
  },
  methods: {
    load() {
      let req = new XMLHttpRequest();
      let that = this;
      req.open("GET", window.location.origin + "/api/user/" + this.$route.params.username);
      req.addEventListener("load", () => {
        if (req.status === 200) {
          that.user = JSON.parse(req.responseText);
          that.notFound = false;
        } else
          that.notFound = true;
      });
      req.send();

      let clipsReq = new XMLHttpRequest();
      clipsReq.open("GET", window.location.origin + "/api/user/" + this.$route.params.username + "/clips");
      clipsReq.addEventListener("load", () => {
        that.clips = JSON.parse(clipsReq.responseText);
      });
      clipsReq.send();
    }
  }
}
</script>

<style scoped>
#root {
  color: white;
  padding: 2rem;
}

#clipList {
  display: flex;
  gap: 1rem;
}
</style>