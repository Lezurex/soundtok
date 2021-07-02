<template>
  <div id="root">
    <div id="logo-container">
      <img src="@/assets/logo_horizontal_dark.svg" alt="logo">
    </div>
    <div id="form-container">
      <div id="form">
        <h3>Login to Soundtok</h3>
        <input v-model="username" type="text" placeholder="Username">
        <input v-model="password" type="password" placeholder="Password">
        <button @click="submit">Submit</button>
        <p style="color: red">{{error}}</p>
        <br/>
        <router-link to="/register">Register</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      username: "",
      password: "",
      error: ""
    }
  },
  methods: {
    submit() {
      // if (this.username.match(/^[\w.-]{3,}$/)) {
      //   if (this.password.match(/^.{8,}$/)) {
      if (!this.username.match(/^\s*$/) && !this.password.match(/^\s*$/)) {
          let req = new XMLHttpRequest();
          req.open("POST", window.location.origin + "/api/auth/login");
          let formData = new FormData();
          formData.append("username", this.username);
          formData.append("password", this.password);
          let that = this;
          req.addEventListener("load", () => {
            if (req.status < 400) {
              console.log("success")
              that.$router.push({name: "Home"});
            } else
              that.error = "Bad credentials";
          });
          req.send(formData);
      } else
        this.error = "Fields must not be empty!"
    }
  }
}
</script>

<style scoped>
#root {
  height: 100vh;
  display: flex;
}

#logo-container {
  box-sizing: border-box;
  background-color: black;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 5rem;
}

#logo-container img {
  width: 30rem;
}

#form-container {
  background-color: white;
  flex-grow: 1;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

input, button {
  display: block;
  margin: 1rem auto;
}
</style>