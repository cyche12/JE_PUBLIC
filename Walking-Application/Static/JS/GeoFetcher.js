/**
 * GeoFetcher
 * Handles communication with the Flask server for analysis requests.
 */
const GeoFetcher = {
  /**
   * Sends an analysis request to Flask.
   * @param {string} address
   * @param {string} view - "satellite" or "street"
   * @returns {Promise<Object>} - Server JSON response
   */
  async sendForAnalysis(address, view) {
    const payload = { address, view };
    console.log("[GeoFetcher] Sending analysis request:", payload);

    const res = await fetch("http://localhost:5000/analyze", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (!res.ok) {
      const errorText = await res.text();
      console.error("[GeoFetcher] Server error:", errorText);
      throw new Error(`Server error: ${res.status}`);
    }

    const json = await res.json();
    console.log("[GeoFetcher] Analysis response:", json);
    return json;
  }
};
