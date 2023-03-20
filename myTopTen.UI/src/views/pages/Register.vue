<template>
  <div>
    <div v-if="showError" class="alert alert-danger alert-dismissible">
      <button
        type="button"
        class="btn-close"
        v-on:click="disableErrorMessage"
      ></button>
      {{ errorResponseMessage }}
    </div>
    <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow class="justify-content-center">
          <CCol :md="9" :lg="7" :xl="6">
            <CCard class="mx-4">
              <CCardBody class="p-4">
                <CForm>
                  <h1>Register</h1>
                  <p class="text-medium-emphasis">Create your account</p>
                  <CInputGroup class="mb-3">
                    <CInputGroupText>
                      <CIcon icon="cil-user" />
                    </CInputGroupText>
                    <CFormInput
                      v-model="username"
                      placeholder="Username"
                      autocomplete="username"
                    />
                  </CInputGroup>
                  <CInputGroup class="mb-3">
                    <CInputGroupText>@</CInputGroupText>
                    <CFormInput
                      v-model="email"
                      placeholder="Email"
                      autocomplete="email"
                    />
                  </CInputGroup>
                  <CInputGroup class="mb-3">
                    <CInputGroupText>
                      <CIcon icon="cil-lock-locked" />
                    </CInputGroupText>
                    <CFormInput
                      v-model="password"
                      type="password"
                      placeholder="Password"
                      autocomplete="new-password"
                    />
                  </CInputGroup>
                  <CInputGroup class="mb-1">
                    <CInputGroupText>
                      <CIcon icon="cil-lock-locked" />
                    </CInputGroupText>
                    <CFormInput
                      v-model="repeatedPassword"
                      type="password"
                      placeholder="Repeat password"
                      autocomplete="new-password"
                    />
                  </CInputGroup>
                  <div class="error" ref="error">{{ errorMessage }}</div>
                  <div class="d-grid">
                    <CButton
                      v-on:click="register"
                      :disabled="!formValid"
                      color="success"
                      >Create Account</CButton
                    >
                  </div>
                </CForm>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  </div>
</template>

<script>
import validator from 'validator'

export default {
  name: 'Register',
  data() {
    return {
      isValidForm: false,
      username: 'sandro',
      email: 'test@test.com',
      password: 'asdf',
      repeatedPassword: 'asdf',
      errorResponseMessage: '',
      showError: false,
    }
  },
  computed: {
    formValid() {
      if (
        this.usernameValid &&
        this.emailValid &&
        this.passwordValid &&
        this.passwordsMatch
      ) {
        return true
      }
      return false
    },
    errorMessage() {
      if (!this.usernameValid && this.username !== '') {
        return 'Invalid username! Username has to contain a minimum of 4 and a maximum of 20 symbols.'
      }
      if (!this.emailValid && this.email !== '') {
        return 'Invalid email!'
      }
      if (!this.passwordValid && this.password !== '') {
        return 'Password does not match requirements! Password has to contain a minimum of 4 and a maximum of 120 symbols.'
      }
      if (!this.passwordsMatch) {
        return 'Passwords do not match!'
      }
      return ''
    },
    usernameValid() {
      return this.username.length >= 4 && this.username.length <= 20
    },
    emailValid() {
      return validator.isEmail(this.email)
    },
    passwordValid() {
      return this.password.length >= 4 && this.password.length <= 120
    },
    passwordsMatch() {
      return this.password === this.repeatedPassword
    },
  },
  methods: {
    register() {
      let userData = {
        username: this.username,
        email: this.email,
        password: this.password,
        role: ['user'],
      }

      this.$store
        .dispatch('userManagement/register', userData)
        .then(() => {
          let userCredentials = {
            username: userData.username,
            password: userData.password,
          }

          this.$store
            .dispatch('userManagement/login', userCredentials)
            .then(() => {
              this.$store.dispatch('userManagement/test')
            })
            .catch((error) => {
              this.errorResponseMessage = error.response.data.message
              this.showErrorMessage()
            })
        })
        .catch((error) => {
          if (error && error.code === 'ERR_NETWORK') {
            this.errorResponseMessage = "Network error! Couldn't reach server!"
          } else {
            this.errorResponseMessage = error.response.data.message
          }
          this.showErrorMessage()
        })
    },
    showErrorMessage() {
      this.showError = true
    },
    disableErrorMessage() {
      this.showError = false
    },
  },
}
</script>

<style scoped>
.error {
  color: red;
  padding-bottom: 5px;
}

.alert {
  margin-bottom: 0;
  position: absolute;
  width: 100vw;
}
</style>
