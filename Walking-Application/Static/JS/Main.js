/**
 * Main.js
 * — unchanged except we now store `slug` in UIHandler so toggle works
 */
document.addEventListener("DOMContentLoaded", () => {
  UIHandler.init();
  document.getElementById("analyzeBtn")
    .addEventListener("click", analyzeAddress);
});

async function analyzeAddress() {
  const address = UIHandler.getCurrentAddress();
  if (!address) {
    alert("Please select an address.");
    return;
  }

  UIHandler.showLoader();
  UIHandler.disableAnalyze(true);
  UIHandler.showDistance("");

  try {
    // 1) POST /analyze (same as before)
    const res = await fetch("http://127.0.0.1:5000/analyze", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ address })
    });
    const data = await res.json();
    if (!res.ok) throw new Error(data.error || "Server error");

    // 2) Show distance (same as before)
    UIHandler.showDistance(
      `Combined-view distance: ${data.distance_m.toFixed(8)} m`
    );

    // 3) Remember slug for toggle
    const slug = address.replace(/\s+/g, "-");
    UIHandler.setLastSlug(slug);

    // 4) Draw overlay for the *current* view
    const view = UIHandler.getCurrentView();
    const overlayRes = await fetch(
      `http://127.0.0.1:5000/parse?slug=${slug}&view=${view}`
    );
    if (overlayRes.ok) {
      const pd = await overlayRes.json();
      OverlayHandler.drawItems(pd);
      OverlayHandler.drawRoute(pd);
    }
  } catch (err) {
    console.error("Analysis failed:", err);
    alert("Analysis error: " + err.message);
  } finally {
    UIHandler.hideLoader();
    UIHandler.disableAnalyze(false);
  }
}
