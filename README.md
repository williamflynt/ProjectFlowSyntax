# Project Flow Syntax

Line-oriented, plaintext grammar for project planning software.

## Development

You'll need to have:

* `git`
* `gcc`
* `tree-sitter` CLI installed and on your PATH (for Gradle build)
* JDK v17.

### Building

The Gradle build script will clone `tree-sitter`, generate your grammar files, and build the project. 

```shell
./gradlew build
```

### The `tree-sitter` CLI

If you have `cargo` or `npm`, you can install that way, or download a release from [the releases page](https://github.com/tree-sitter/tree-sitter/releases/latest).

```shell
npm install -g tree-sitter-cli
```

Make sure the `tree-sitter` binary and `node` are on your `PATH`!
Here's an option for Linux:

```shell
sudo ln -s $(which tree-sitter) /usr/bin/tree-sitter
sudo ln -s $(which node) /usr/bin/node
```

For more information, see the [README for CLI](https://github.com/tree-sitter/tree-sitter/tree/master/cli) on GitHub.
