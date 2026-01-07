const MapManager = {
  map: null,
  marker: null,
  featureLayer: null,
  obstacleLayer: null,
  routeLayer: null,
  baseLayers: {},

  initMap() {
    this.map = L.map("map", {
      center: [45.4215, -75.6972],
      zoom: 13,
      layers: []
    });

    const esriImagery = L.tileLayer(
      "https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}",
      { maxZoom: 19, attribution: "© Esri" }
    );

    esriImagery.addTo(this.map);

    this.baseLayers = {
      "Esri Imagery": esriImagery
    };

    L.control.layers(this.baseLayers).addTo(this.map);
  },

  setMarker(lat, lon, popupText) {
    if (this.marker) this.map.removeLayer(this.marker);
    this.marker = L.marker([lat, lon]).addTo(this.map).bindPopup(popupText).openPopup();
    this.map.setView([lat, lon], 19);
  },

  clearLayers() {
    if (this.featureLayer) this.map.removeLayer(this.featureLayer);
    if (this.obstacleLayer) this.map.removeLayer(this.obstacleLayer);
    if (this.routeLayer) this.map.removeLayer(this.routeLayer);
    this.featureLayer = null;
    this.obstacleLayer = null;
    this.routeLayer = null;
  },

  addGeoFeatures(geojson, type = "door") {
    this.clearFeatureLayer();
    this.featureLayer = L.geoJSON(geojson, {
      onEachFeature: (feature, layer) => {
        const icon = type === "curb" ? "🟧" : "🚪";
        layer.bindPopup(`${icon} ${type.toUpperCase()}: ${feature.properties.address}`);
      },
      pointToLayer: (feature, latlng) => {
        return L.circleMarker(latlng, {
          radius: 6,
          color: type === "curb" ? "#f75f00" : "#3388ff",
          fillOpacity: 0.8
        });
      }
    });
    this.featureLayer.addTo(this.map);
  },

  clearFeatureLayer() {
    if (this.featureLayer) {
      this.map.removeLayer(this.featureLayer);
      this.featureLayer = null;
    }
  },

  clearObstacleLayer() {
    if (this.obstacleLayer) {
      this.map.removeLayer(this.obstacleLayer);
      this.obstacleLayer = null;
    }
  },

  addObstaclePolygons(geojson) {
    this.clearObstacleLayer();

    this.obstacleLayer = L.geoJSON(geojson, {
      style: {
        color: 'red',
        weight: 2,
        fillColor: '#f03',
        fillOpacity: 0.3
      },
      onEachFeature: (feature, layer) => {
        if (feature.properties && feature.properties.label) {
          layer.bindPopup(`Boundary: ${feature.properties.label}`);
        }
      }
    });

    this.obstacleLayer.addTo(this.map);
  },

  addRouteLine(geojson) {
    if (this.routeLayer) this.map.removeLayer(this.routeLayer);
    this.routeLayer = L.geoJSON(geojson, {
      style: { color: 'green', weight: 4 }
    }).addTo(this.map);
  }
};
