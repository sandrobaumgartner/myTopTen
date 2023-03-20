<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="6">
          <div class="center clearfix">
            <h1 class="display-3 me-4">Oops! 403</h1>
            <h4 class="pt-1">Access denied.</h4>
            <CButton v-on:click="navigateBackToLogin" class="pt-2" color="info"
              >Go back to login</CButton
            >
          </div>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>

<script>
import router from '@/router'

export default {
  name: 'Forbidden',
  methods: {
    navigateBackToLogin() {
      router.push('login')
    },
  },
}
</script>

<style scoped>
.center {
    text-align: center;
}
</style>
