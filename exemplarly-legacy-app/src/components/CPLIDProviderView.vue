<template>
  <div class="cplID-provision">
    <h1 style="margin-left: 50px; margin-top: -20px">LOCATION CPL-ID LOOKUP</h1>
    <div style="margin-top: 10px">
      <div class="input-field-block">
        <label>POSTAL CODE:</label>
        <input v-model="postalCode" placeholder="" name="postalCode" />
      </div>

      <div class="input-field-block">
        <label>PLACE:</label>
        <input v-model="place" placeholder="" name="place" />
      </div>

      <div class="input-field-block">
        <label>DISTRICT:</label>
        <input v-model="district" placeholder="" name="district" />
      </div>

      <div class="input-field-block">
        <label>STREET:</label>
        <input v-model="street" placeholder="" name="street" />
      </div>

      <div class="input-field-block">
        <label>STREET NUMBER:</label>
        <input v-model="streetNumber" placeholder="" name="streetNumber" />
      </div>

      <div class="input-field-block">
        <label>STREET SUFFIX:</label>
        <input v-model="streetSuffix" placeholder="" name="streetSuffix" />
        <button type="button" class="btn" style="margin-left: 100px" @click="getId()">Get CPL-ID</button> 
      </div>

      <div class="input-field-block">
        <label style="color: orange; font-size: 30px">CPL-ID:</label>
        <input
          style="border: 0px; color: orange"
          v-model="cplID"
          placeholder=""
          name="cplID"
          readonly
        />
      </div>

      <div style="height: 20px">
        <br style="color: blue" />
      </div>

      <div style="height: 1px; background-color: white"></div>

      <div style="height: 20px">
        <br style="color: blue" />
      </div>

      <div class="input-field-block">
        <label style="color: red; font-size: 30px">Error Messages: </label>
        <input style="border: 0px; color: red" v-model="errorMessage" placeholder="" name="errorMessage" readonly />
      </div>

      <div style="display: inline-block; text-align: left; padding: 20px;">
        <label style="color: yellow; font-size: 20px">PF KEYS: </label>
        <p style="display: inline-block; font-size: 20px; margin-left: 30px; color: yellow">PF1 (Help)</p>
        <p style="display: inline-block; font-size: 20px; margin-left: 30px; color: yellow">|</p>
        <p style="display: inline-block; font-size: 20px; margin-left: 30px; color: yellow">PF2 (Update)</p>
        <p style="display: inline-block; font-size: 20px; margin-left: 30px; color: yellow">|</p>
        <p style="display: inline-block; font-size: 20px; margin-left: 30px; color: yellow">PF3 (Exit)</p>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "CPLIDProviderView",
  data() {
    return {
      postalCode: "",
      place: "",
      district: "",
      street: "",
      streetNumber: "",
      streetSuffix: "",
      cplID: "",
      errorMessage: "",
    };
  },
  methods: {
      getId(){
          if(!(Math.random() < 0.1)){
            this.errorMessage = "";
            this.cplID = "" + Date.now();
          } else {
              this.cplID = "";
              this.errorMessage = "CPL-ID could not be found with the help of the system."
          }
      }
  }
};
</script>

<style>
html, body {
    max-width: 100%;
    overflow-x: hidden;
}
.cplID-provision {
  background-color: black;
  text-align: left;
  font-family: Bookmann;
}
.input-field-block {
  font-family: Bookmann;
  font-size: 20px;
  display: inline-block;
  text-align: left;
  width: 100%;
  /*border: 1px solid;*/
  padding: 20px;
}
.input-field-block label {
  width: 200px;
  display: inline-block;
  color: rgb(211, 238, 247);
}
.input-field-block input {
  font-size: 20px;
  background-color: black;
  color: rgb(211, 238, 247);
  margin-left: 100px;
  width: 500px;
  border-bottom: 1px solid green;
  border-top: 1px;
  border-left: 1px;
  border-right: 1px;
}
.input-field-block input:focus {
  outline: none;
}
.btn {
    background: #7C7C7C;
	border-bottom: 6px inset rgba(0,0,0,.5);
	border-left: 6px inset rgba(0,0,0,.5);
	border-right: 6px inset rgba(255,255,255,.5);
	border-top: 6px inset rgba(255,255,255,.5);
	box-sizing: border-box;
	color: white;
	cursor: pointer;
	display: inline-block;
	font-size: 1.5rem;
	margin: 1rem;
	min-width: 200px;
	padding: .5rem;
	text-transform: uppercase;
	width: auto;
}
.btn:hover {
    background: #BCBCBC;
}
</style>