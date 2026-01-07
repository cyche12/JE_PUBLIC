/**
 * UIHandler
 * — tracks last analysis slug so toggle can re-draw overlay
 */
const UIHandler = (() => {
  const addrSel      = document.getElementById("addressSelect");
  const toggleBtn    = document.getElementById("toggleViewBtn");
  const analyzeBtn   = document.getElementById("analyzeBtn");
  const previewImg   = document.getElementById("preview");
  const loader       = document.getElementById("loader");
  const distanceDisp = document.getElementById("distanceDisplay");
  const overlay      = document.getElementById("overlay");

  let currentView = "street";
  let lastSlug = null;         // ← track what we last analyzed

  function buildImageURL(address, view) {
    const slug = address.replace(/\s+/g, "-");
    return `train/images/${slug}${view === "street" ? "-1.jpg" : "-2.jpg"}`;
  }

  function clearOverlay() {
    const ctx = overlay.getContext("2d");
    ctx.clearRect(0, 0, overlay.width, overlay.height);
  }

  function onAddressChange() {
    const addr = addrSel.value;
    currentView = "street";
    previewImg.src = buildImageURL(addr, currentView);
    toggleBtn.disabled  = !addr;
    analyzeBtn.disabled = !addr;
    toggleBtn.textContent = "Show Satellite View";
    distanceDisp.textContent = "";
    clearOverlay();
    lastSlug = null;
  }

  function onToggleView() {
    const addr = addrSel.value;
    if (!addr) return;

    currentView = (currentView === "street") ? "satellite" : "street";
    previewImg.src = buildImageURL(addr, currentView);
    toggleBtn.textContent = currentView === "street"
      ? "Show Satellite View"
      : "Show Street View";
    distanceDisp.textContent = "";
    clearOverlay();

    // if we've already analyzed, re-draw overlay for new view
    if (lastSlug) {
      fetch(`/parse?slug=${lastSlug}&view=${currentView}`)
        .then(r => r.ok ? r.json() : Promise.reject(r.status))
        .then(pd => {
          OverlayHandler.drawItems(pd);
          OverlayHandler.drawRoute(pd);
        })
        .catch(e => console.warn("Overlay toggle failed:", e));
    }
  }

  return {
    init() {
      addrSel.addEventListener("change", onAddressChange);
      toggleBtn.addEventListener("click", onToggleView);
      onAddressChange();
    },
    getCurrentAddress() { return addrSel.value.trim(); },
    getCurrentView()    { return currentView; },
    setLastSlug(slug)   { lastSlug = slug; },
    disableAnalyze(f)   { analyzeBtn.disabled = f; },
    showLoader()        { loader.style.display = "block"; },
    hideLoader()        { loader.style.display = "none"; },
    showDistance(txt)   { distanceDisp.textContent = txt; }
  };
})();
