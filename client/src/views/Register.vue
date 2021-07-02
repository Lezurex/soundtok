<template>
  <div id="root">
    <div id="logo-container">
      <img src="@/assets/logo_horizontal_dark.svg" alt="logo">
    </div>
    <div id="form-container">
      <div id="form">
        <h3>Register for Soundtok</h3>
        <input v-model="email" type="email" placeholder="eMail">
        <input v-model="username" type="text" placeholder="Username">
        <input v-model="password" type="password" placeholder="Password">
        <button @click="submit">Register</button>
        <p style="color: red">{{error}}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      username: "",
      password: "",
      email: "",
      error: ""
    }
  },
  methods: {
    submit() {
      if (this.username.match(/^[\w.-]{3,}$/)) {
        if (this.password.match(/^.{8,}$/)) {
          let req = new XMLHttpRequest();
          req.open("POST", window.location.origin + "/api/auth/register");
          let formData = new FormData();
          formData.append("username", this.username);
          formData.append("password", this.password);
          formData.append("email", this.email);
          let that = this;
          req.addEventListener("load", () => {
            if (req.status < 400) {
              let loginReq = new XMLHttpRequest();
              loginReq.open("POST", window.location.origin + "/api/auth/login");
              loginReq.addEventListener("load", () => {
                if (req.status < 400) {
                  console.log("success")
                  that.$router.push({name: "Home"});
                }
              });
              req.send(formData);
            } else
              that.error = "Bad credentials";
          });
          req.send(formData);
        } else
          this.error = "Password must be at lease 8 characters long!"
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