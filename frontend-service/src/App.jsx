import React from "react";
import { BrowserRouter, Routes, Route } from "react-router";
import IndexPage from "./pages/IndexPage";

const App = () => {
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-[var(--background-color)] text-[var(--primary-text-color)]">
        <Routes>
          <Route path="/" element={<IndexPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;
