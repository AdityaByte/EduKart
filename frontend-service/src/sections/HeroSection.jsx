import React from "react";
import Tag from "../components/Tag";

const HeroSection = () => {
  const tags = ["Smart Learning", "Knowledge Hub", "Digital Education"];

  return (
    <section className="relative w-full min-h-screen flex flex-col justify-center items-center px-6 text-center bg-[var(--background-color)] overflow-hidden">

      {/* Subtle gradient overlay */}
      <div className="absolute inset-0 bg-gradient-to-b from-[var(--background-color)] via-transparent to-[var(--foreground-color)] opacity-10 pointer-events-none" />

      <div className="relative z-10 max-w-3xl flex flex-col gap-6">
        <h1 className="text-4xl md:text-6xl font-extrabold leading-tight text-[var(--primary-text-color)]">
          Unlock <span className="text-[var(--foreground-color)]">Smarter Learning</span> with EduKart
        </h1>

        <p className="text-lg md:text-xl font-medium text-[var(--secondary-text-color)] max-w-xl mx-auto">
          A professional learning platform offering curated notes, projects, and study material to help you grow with confidence.
        </p>

        {/* Tags */}
        <div className="flex flex-wrap justify-center gap-3 mt-4">
          {tags.map((tag, index) => (
            <Tag key={index} text={tag} />
          ))}
        </div>

        {/* Buttons */}
        <div className="flex flex-wrap gap-4 justify-center mt-10">
          <button className="px-8 py-3 rounded-lg bg-[var(--foreground-color)] text-white font-semibold shadow-lg hover:shadow-xl hover:opacity-90 transition-all duration-300">
            Get Started
          </button>
          <button className="px-8 py-3 rounded-lg border-2 border-[var(--foreground-color)] text-[var(--foreground-color)] font-semibold hover:bg-[var(--foreground-color)] hover:text-white transition-all duration-300">
            Learn More
          </button>
        </div>
      </div>

      {/* Optional decorative shapes for more premium feel */}
      <div className="absolute top-0 left-1/2 w-96 h-96 bg-[var(--foreground-color)] opacity-5 rounded-full -translate-x-1/2 -translate-y-1/3 blur-3xl pointer-events-none" />
      <div className="absolute bottom-0 right-1/3 w-72 h-72 bg-[var(--foreground-color)] opacity-5 rounded-full translate-x-1/2 translate-y-1/3 blur-3xl pointer-events-none" />
    </section>
  );
};

export default HeroSection;
