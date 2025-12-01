"use client";

import HeroSection from "@/components/hero-section";
import { SoftGradientBackground } from "@/components/ui/gradient-bg";


const page = () => {
  return (
    <div className="relative">
      <SoftGradientBackground />
      <HeroSection />
    </div>
  );
}

export default page;