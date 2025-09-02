import React from "react";
import Navbar from "../components/Navbar";
import HeroSection from "../sections/HeroSection";
import ProductSection from "../sections/ProductSection";
import Testimonials from "../sections/Testimonials";

const IndexPage = () => {
  return (
    <>
      <Navbar />

      {/* Hero */}
      <section className="relative">
        <HeroSection />
        {/* Decorative wave divider */}
        <div className="absolute bottom-0 left-0 w-full overflow-hidden leading-[0]">
          <svg
            className="relative block w-full h-24 text-[var(--foreground-color)]"
            xmlns="http://www.w3.org/2000/svg"
            preserveAspectRatio="none"
            viewBox="0 0 1200 120"
          >
            <path
              d="M321.39 56.44c58-10.79 114-30.14 172-41.92s118-14.54 175.6 2.18 107.8 53.68 162.6 72.74 108.4 23.15 162.6 9.42c54.2-13.73 108.4-46.47 162.6-51.51s108.4 15.56 162.6 32.58v51.47H0V60.12c107.8 7.44 214-3.89 321.39-3.68z"
              className="fill-current"
            ></path>
          </svg>
        </div>
      </section>

    <ProductSection />
    <Testimonials />
    </>
  );
};

export default IndexPage;
