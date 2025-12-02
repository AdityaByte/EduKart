import {
    Card,
    CardAction,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
  } from "./card";

  export default function ProductCard({
    tag,
    name,
    desc,
  }: {
    tag: string;
    name: string;
    desc: string;
  }) {
    return (
      <Card
        className="
          max-w-[360px] w-[340px] mx-auto rounded-2xl
          bg-white border border-gray-200 shadow-sm
          hover:shadow-lg hover:border-gray-300
          transition-all duration-300 cursor-pointer p-0
        "
      >
        {/* Minimal Badge Header */}
        <CardHeader className="p-5 pb-3">
          <span
            className="
              inline-block text-[10px] font-semibold text-[#2E2E2E]
              bg-[#F1F1F1] px-2.5 py-1 rounded-full uppercase
              tracking-wider shadow-sm
            "
          >
            {tag}
          </span>

          <CardTitle className="text-xl font-semibold mt-3 leading-tight">
            {name}
          </CardTitle>

          <CardDescription className="text-gray-600 leading-relaxed mt-2">
            {desc}
          </CardDescription>
        </CardHeader>

        {/* Optional Content Area */}
        <CardContent className="px-5 pb-0">
          {/* Add extra items later like tag chips, rating, category */}
        </CardContent>

        {/* Footer Action */}
        <CardFooter className="p-5 pt-3">
          <CardAction
            className="
              w-full py-2.5 text-sm font-medium rounded-xl
              bg-[#171717] text-white text-center
              hover:bg-black active:scale-[0.98]
              transition-all duration-300 shadow-sm
            "
          >
            View Details
          </CardAction>
        </CardFooter>
      </Card>
    );
  }
