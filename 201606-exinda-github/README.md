# Build slides, grab reveal.js dependency

```
pandoc -s --mathjax -i -t revealjs SLIDES -o SLIDES.html
curl -O -L https://github.com/hakimel/reveal.js/archive/3.3.0.tar.gz
tar xfz 3.3.0.tar.gz
rm 3.3.0.tar.gz
mv reveal-js-* reveal.js
```

# Create PDF

Go to <file:///home/nequi/dev/presentations/201606-exinda-github/SLIDES.html?print-pdf> and print the page
