# lein-gitify

This is a Leiningen plugin for creating and initializing a Github repository.

This is a lein 2 plugin. It will not work for lein 1, and I do not wish to
support lein 1. Upgrade, grandpa.

## Installation

Put `[lein-gitify "0.1.0"]` into the `:plugins` vector of your
`:user` profile in `~/.lein/profiles.clj`.

This plugin requires that you have set your Github username and password in
your git config.

```
git config --get github.user
git config --get github.password
```

Github no longer supports token based authentication in its API, and we are
thus required to use a password. If you don't want to store your password
in your git config, please send me a pull request with a better idea for
configuration that is equally as simplistic as setting a git config option.

## Usage

This plugin only works in a project. When you create a new project,
(with `lein new` for example), cd into that project and run `lein gitify`.
This will create a Github repository under your account, run `git init`,
and set up your origin remote to point to the new Github repo. If you pass
the `--init` option, it'll do the following as well:

```
git add .
git commit -am 'First commit.'
git push origin master
```

Thus creating and pushing your first commit. It will not do this unless you
pass the `--init` option.

## License

Copyright © 2012 Anthony Grimes

Distributed under the Eclipse Public License, the same as Clojure.
