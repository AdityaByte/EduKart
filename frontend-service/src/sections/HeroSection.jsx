import React from "react";

const HeroSection = () => {
  return (
    <div className="w-full h-screen bg-[var(--background-color)] flex justify-center items-center flex-col gap-8 text-center px-6">

      <h1 className="text-4xl md:text-6xl font-extrabold bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 bg-clip-text text-transparent animate-fadeIn">
        Learn Smart, Grow Fast with EduKart
      </h1>

      <p className="text-lg md:text-2xl font-medium text-[var(--secondary-text-color)] max-w-2xl animate-fadeIn delay-200">
        Discover notes, projects, and study material crafted to fuel your success
      </p>

      <div className="flex gap-4 mt-6 animate-fadeIn delay-500">
        <button className="px-6 py-3 rounded-xl bg-[var(--foreground-color)] text-white font-semibold shadow-lg hover:scale-105 transition">
          Get Started
        </button>
        <button className="px-6 py-3 rounded-xl border border-[var(--foreground-color)] text-[var(--foreground-color)] font-semibold hover:bg-[var(--foreground-color)] hover:text-white transition">
          Explore More
        </button>
      </div>
    </div>
  );
};

export default HeroSection;
