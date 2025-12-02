"use client";

import HeroSection from "@/components/hero-section";
import LatestProductSection from "@/components/latest-product-section";
import Testimonials from "@/components/testimonials";

const page = () => {
  return (
    <div>
      <HeroSection />
      <LatestProductSection />
      <Testimonials />
    </div>
  );
}

export default page;