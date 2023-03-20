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
          <CCol :md="8">
            <CCardGroup>
              <CCard class="p-4">
                <CCardBody>
                  <CForm>
                    <h1>Login</h1>
                    <p class="text-medium-emphasis">Sign In to your account</p>
                    <CInputGroup class="mb-3">
                      <CInputGroupText>
                        <CIcon icon="cil-user" />
                      </CInputGroupText>
                      <CFormInput
                        v-on:keydown.enter="login"
                        v-model="username"
                        placeholder="Username"
                        autocomplete="username"
                      />
                    </CInputGroup>
                    <CInputGroup class="mb-4">
                      <CInputGroupText>
                        <CIcon icon="cil-lock-locked" />
                      </CInputGroupText>
                      <CFormInput
                        v-on:keydown.enter="login"
                        v-model="password"
                        type="password"
                        placeholder="Password"
                        autocomplete="current-password"
                      />
                    </CInputGroup>
                    <CRow>
                      <CCol :xs="6">
                        <CButton
                          v-on:click="login"
                          color="primary"
                          class="px-4"
                        >
                          Login
                        </CButton>
                      </CCol>
                    </CRow>
                  </CForm>
                </CCardBody>
              </CCard>
              <CCard class="text-white bg-primary py-5" style="width: 44%">
                <CCardBody class="text-center">
                  <div class="mt-4">
                    <h2>Sign up</h2>
                    <p>Don't have an account yet? Sign up!</p>
                    <CButton
                      v-on:click="navigateToRegisterPage"
                      color="light"
                      variant="outline"
                      class="mt-1"
                    >
                      Register
                    </CButton>
                  </div>
                </CCardBody>
              </CCard>
            </CCardGroup>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  </div>
</template>

<script>
import router from '@/router'

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      errorResponseMessage: '',
      showError: false,
    }
  },
  methods: {
    navigateToRegisterPage() {
      router.push('/pages/register')
    },
    login() {
      let userCredentials = {
        username: this.username,
        password: this.password,
      }

      this.$store
        .dispatch('userManagement/login', userCredentials)
        .then(() => {
          router.push('/home')
        })
        .catch((error) => {
          this.errorResponseMessage = error.response.data.message
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
.alert {
  margin-bottom: 0;
  position: absolute;
  width: 100vw;
}
</style>
