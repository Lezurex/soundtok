<template>
  <div id="nav" v-if="showBar">
    <img src="@/assets/logo_horizontal_dark.svg" alt="Logo">
    <router-link to="/create">Create your own!</router-link>
  </div>
  <router-view/>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      showBar: false
    }
  },
  mounted() {
    let req = new XMLHttpRequest();
    let that = this;
    req.open("GET", window.location.origin + "/api/auth");
    req.addEventListener("load", () => {
      if (!JSON.parse(req.responseText).authenticated) {
        that.$router.push({name: 'Login'});
      }
    });
    req.send();
    console.log(this.$route)
    if (!this.$route.fullPath.startsWith("/login") && !this.$route.fullPath.startsWith("/register")) {
      this.showBar = true;
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: #2e2e2e;
}

html, body {
  margin: 0;
}

#nav {
  padding: 30px;
  background-color: #000000;
}

#nav img {
  height: 100px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
