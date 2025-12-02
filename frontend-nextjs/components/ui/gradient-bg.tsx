"use client";

export function SoftGradientBackground() {
  return (
    <div className="absolute inset-0 -z-10 pointer-events-none overflow-hidden">
      {/* Base background */}
      <div className="absolute inset-0 bg-[#f5f5f7]" />

      {/* Blob 1 */}
      <div
        className="
        absolute left-0 top-0
        w-[80vw] h-[90vh]
        -rotate-45 -translate-y-24
        rounded-full
        bg-[radial-gradient(70%_70%_at_50%_40%,rgba(200,200,200,0.45)_0,rgba(160,160,160,0.15)_60%,transparent_100%)]
        sm:w-[60vw] sm:h-[80vh]
        lg:w-[600px] lg:h-[900px]
      "
      />

      {/* Blob 2 */}
      <div
        className="
        absolute left-[10vw] top-10
        w-[60vw] h-[70vh]
        -rotate-45 translate-y-10
        rounded-full
        bg-[radial-gradient(60%_60%_at_50%_50%,rgba(220,220,220,0.35)_0,rgba(150,150,150,0.1)_70%,transparent_100%)]
        sm:left-20 sm:w-[45vw] sm:h-[65vh]
        lg:left-20 lg:w-[400px] lg:h-[700px]
      "
      />

      {/* Blob 3 */}
      <div
        className="
        absolute left-[20vw] top-0
        w-[65vw] h-[70vh]
        -rotate-45 -translate-y-10
        rounded-full
        bg-[radial-gradient(50%_50%_at_50%_50%,rgba(180,180,180,0.3)_0,rgba(120,120,120,0.08)_80%,transparent_100%)]
        sm:left-40 sm:w-[50vw] sm:h-[60vh]
        lg:left-40 lg:w-[450px] lg:h-[700px]
      "
      />
    </div>
  );
}
