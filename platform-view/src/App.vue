<template>
  <v-app>
    <v-app-bar
      style="position: relative"
      app
      color="grey darken-3"
      height="100px"
      clipped-left
      flat
    >
      <v-toolbar-title class="white--text display-1">
        RPA Platform by viadee
      </v-toolbar-title>
    </v-app-bar>

    <div style="width: 100%; display: table; margin-top: -103px">
      <div style="display: table-row">
        <v-navigation-drawer
          style="position: relative; display: table-cell"
          absolute
          app
          color="grey lighten-2"
          clipped-right
        >
          <v-list class="" dense>
            <v-list-item
              class="pb-4 pt-4"
              v-for="item in menuItems"
              :key="item.title"
              :to="item.path"
            >
              <v-list-item-icon>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title>{{ item.title }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>

        <v-main style="display: table-cell">
          <router-view />
        </v-main>
      </div>
    </div>
  </v-app>
</template>

<script>
export default {
  name: "App",

  data() {
    return {
      menuItems: [
        {
          title: "Process Overview",
          icon: "mdi-forum",
          path: "/overview",
        },
        {
          title: "RPA Prioritization",
          icon: "mdi-forum",
          path: "/rpa"
        },
        {
          title: "Migration",
          icon: "mdi-forum",
          path: "/migration",
        },
      ],
    };
  },
};
</script>